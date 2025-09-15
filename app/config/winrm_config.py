import logging

from app.config import ConfigManager, BaseConfig

logger = logging.getLogger(__name__)


class WinrmConfig(BaseConfig):
    TRANSPORT = None
    ADDRESS = None
    PORT = None
    USERNAME = None
    PASSWORD = None

    @classmethod
    def initialize(cls):
        cls.TRANSPORT = ConfigManager.get_env_var("MW_WINRM_TRANSPORT", "ntlm")
        cls.ADDRESS = ConfigManager.get_env_var("MW_WINRM_ADDRESS", "192.168.0.2")
        default_port = 5986 if cls.TRANSPORT.lower() == "ssl" else 5985
        cls.PORT = ConfigManager.get_env_var("MW_WINRM_PORT", default_port, int)
        cls.USERNAME = ConfigManager.get_env_var("MW_WINRM_USERNAME", "admin")
        cls.PASSWORD = ConfigManager.get_env_var("MW_WINRM_PASSWORD", "p@ssw0rd")
        cls.print_config_info()
