import os

from dotenv import load_dotenv

load_dotenv()

EUREKA_URI = os.getenv("EUREKA_URI", "")
EUREKA_USERNAME = os.getenv("EUREKA_USERNAME", "")
EUREKA_PASSWORD = os.getenv("EUREKA_PASSWORD", "")

PATH_FILE_MODEL = os.getenv("PATH_FILE_MODEL", "")
PATH_FILE_TEMP = os.getenv("PATH_FILE_TEMP", "")
