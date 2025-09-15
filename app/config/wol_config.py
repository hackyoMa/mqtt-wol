import logging
import re

from app.config import ConfigManager, BaseConfig

logger = logging.getLogger(__name__)


class WolConfig(BaseConfig):
    ADDRESS = None
    MAC_ADDRESS = None

    @classmethod
    def initialize(cls):
        cls.ADDRESS = ConfigManager.get_env_var("MW_WOL_ADDRESS", "255.255.255.255")

        mac_address = ConfigManager.get_env_var("MW_WOL_MAC_ADDRESS", "00:00:00:FF:FF:FF")
        cleaned_mac = re.sub(r"[^A-Fa-f0-9]", "", mac_address).upper()
        if len(cleaned_mac) != 12:
            raise ValueError("Invalid MW_WOL_MAC_ADDRESS format")
        cls.MAC_ADDRESS = cleaned_mac
        cls.print_config_info()
