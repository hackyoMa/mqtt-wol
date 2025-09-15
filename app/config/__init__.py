import logging
import os
from typing import Any, Callable, Optional

logger = logging.getLogger(__name__)


class ConfigManager:
    @staticmethod
    def get_env_var(key: str, default: Any = None, converter: Optional[Callable] = None, required: bool = False) -> Any:
        value = os.getenv(key)
        if value is None or value.strip() == "":
            if required:
                raise ValueError(f"Required environment variable {key} is not set")
            return default
        if converter:
            try:
                return converter(value)
            except ValueError as e:
                raise ValueError(f"Invalid format for {key}: {str(e)}")
        return value


class BaseConfig:
    @classmethod
    def initialize(cls):
        raise NotImplementedError("Subclasses must implement initialize method")

    @classmethod
    def print_config_info(cls):
        config_items = {k: v for k, v in cls.__dict__.items() if not k.startswith('_') and not callable(v)}
        logger.info(f"{cls.__name__} configuration: {config_items}")
