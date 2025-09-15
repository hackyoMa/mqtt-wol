import asyncio
import logging
import signal
import threading

import paho.mqtt.client as mqtt
from paho.mqtt.enums import CallbackAPIVersion

from app.config.mqtt_config import MqttConfig
from app.config.winrm_config import WinrmConfig
from app.config.wol_config import WolConfig
from app.util.winrm_util import power_off
from app.util.wol_util import power_on

logger = logging.getLogger(__name__)


class MqttClient:
    def __init__(self):
        self.qos = 1
        self.on_command = "on"
        self.off_command = "off"
        self.client = None
        self.loop = asyncio.get_event_loop()
        self.shutdown_event = threading.Event()

    def on_connect(self, _client, _userdata, _flags, reason_code, _properties):
        if reason_code.is_failure:
            logger.error(f"Failed to connect to server {MqttConfig.SERVER}: {reason_code}")
        else:
            logger.info(f"Successfully connected to server {MqttConfig.SERVER}")
            _client.subscribe(topic=MqttConfig.TOPIC, qos=self.qos)

    def on_subscribe(self, _client, _userdata, _mid, reason_code_list, _properties):
        if reason_code_list[0].is_failure:
            logger.error(f"Failed to subscribe to topic {MqttConfig.TOPIC}")
        else:
            logger.info(f"Subscribing to the topic {MqttConfig.TOPIC} was successful")

    def on_message(self, _client, _userdata, message):
        msg = message.payload.decode("utf-8")
        if not msg:
            logger.info("Received empty or blank message")
            return
        logger.info(f"Received message with topic {MqttConfig.TOPIC}: {msg}")
        asyncio.run_coroutine_threadsafe(self.process_message(msg.strip().lower()), self.loop)

    async def process_message(self, cmd: str):
        if cmd == self.on_command:
            self.process_power_on()
        elif cmd == self.off_command:
            self.process_power_off()
        else:
            logger.info(f"Undefined operations: {cmd}")

    @staticmethod
    def process_power_on():
        try:
            logger.info("Start booting up")
            power_on(WolConfig.ADDRESS, WolConfig.MAC_ADDRESS)
            logger.info("Bootup successful")
        except Exception as e:
            logger.error(f"Bootup failed: {str(e)}")

    @staticmethod
    def process_power_off():
        try:
            logger.info("Start shutting down")
            power_off(WinrmConfig.ADDRESS, WinrmConfig.PORT, WinrmConfig.TRANSPORT, WinrmConfig.USERNAME,
                      WinrmConfig.PASSWORD)
            logger.info("Shutdown successful")
        except Exception as e:
            logger.error(f"Shutdown failed: {str(e)}")

    def graceful_shutdown(self, _signum, _frame):
        if self.client:
            logger.info(f"Unsubscribe")
            self.client.unsubscribe(MqttConfig.TOPIC)
            self.client.loop_stop()
            logger.info(f"Disconnect")
            self.client.disconnect()
            self.loop.stop()
        self.shutdown_event.set()

    def init_listener(self):
        signal.signal(signal.SIGINT, self.graceful_shutdown)
        signal.signal(signal.SIGTERM, self.graceful_shutdown)
        threading.Thread(target=self.loop.run_forever, daemon=True).start()

        self.client = mqtt.Client(callback_api_version=CallbackAPIVersion.VERSION2, client_id=MqttConfig.CLIENT_ID,
                                  protocol=mqtt.MQTTv311)
        if MqttConfig.USE_SSL:
            self.client.tls_set()
        self.client.on_connect = self.on_connect
        self.client.on_subscribe = self.on_subscribe
        self.client.on_message = self.on_message
        self.client.connect(MqttConfig.SERVER, MqttConfig.PORT)
        try:
            self.client.loop_forever()
        except KeyboardInterrupt:
            self.graceful_shutdown(signal.SIGINT, None)
        self.shutdown_event.wait()
