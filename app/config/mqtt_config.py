import logging

from app.config import ConfigManager, BaseConfig

logger = logging.getLogger(__name__)


class MqttConfig(BaseConfig):
    SERVER = None
    PORT = None
    USE_SSL = None
    CLIENT_ID = None
    VERSION = None
    TOPIC = None

    @classmethod
    def initialize(cls):
        cls.SERVER = ConfigManager.get_env_var("MW_MQTT_SERVER", "bemfa.com")
        cls.PORT = ConfigManager.get_env_var("MW_MQTT_PORT", 9503, int)
        cls.USE_SSL = ConfigManager.get_env_var("MW_MQTT_USE_SSL", "true", lambda x: x.lower() == "true")
        cls.CLIENT_ID = ConfigManager.get_env_var("MW_MQTT_CLIENT_ID", "00000000000000000000000000000000")
        cls.VERSION = ConfigManager.get_env_var("MW_MQTT_VERSION", 3, int)
        cls.TOPIC = ConfigManager.get_env_var("MW_MQTT_TOPIC", "pc006")
        cls.print_config_info()
