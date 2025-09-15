import logging
import sys

from app.config.mqtt_config import MqttConfig
from app.config.winrm_config import WinrmConfig
from app.config.wol_config import WolConfig
from app.mqtt_client import MqttClient


def main():
    logging.basicConfig(level=logging.INFO, format="%(asctime)s - %(name)s - %(levelname)s - %(message)s",
                        handlers=[logging.StreamHandler()])
    logger = logging.getLogger(__name__)
    try:
        MqttConfig.initialize()
        WinrmConfig.initialize()
        WolConfig.initialize()
        mqtt_client = MqttClient()
        mqtt_client.init_listener()
    except Exception as e:
        logger.error(f"Unhandled exception occurred: {str(e)}")
        sys.exit(1)
    finally:
        logging.info("Application shutdown complete")


if __name__ == "__main__":
    main()
