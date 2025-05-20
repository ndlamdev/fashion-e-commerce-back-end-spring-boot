import json
import os
import socket

import requests
from dotenv import load_dotenv

load_dotenv()

EUREKA_URI = os.getenv("EUREKA_URI", "")
EUREKA_USERNAME = os.getenv("EUREKA_USERNAME", "")
EUREKA_PASSWORD = os.getenv("EUREKA_PASSWORD", "")

def register_to_eureka(app_name="python-grpc-service", port=8113, eureka_server=f"https://{EUREKA_USERNAME}:{EUREKA_PASSWORD}@{EUREKA_URI}/eureka/apps"):
    instance_id = f"{socket.gethostbyname(socket.gethostname())}:{app_name}:{port}"
    payload = {
        "instance": {
            "hostName": socket.gethostname(),
            "app": app_name.upper(),
            "ipAddr": socket.gethostbyname(socket.gethostname()),
            "vipAddress": app_name.lower(),
            "status": "UP",
            "port": {
                "$": port,
                "@enabled": "true"
            },
            "dataCenterInfo": {
                "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
                "name": "MyOwn"
            },
            "instanceId": instance_id
        }
    }

    print(f"✅ Connect {app_name} with Eureka!")

    headers = {'Content-Type': 'application/json'}
    url = f"{eureka_server}/{app_name.upper()}"
    response = requests.post(url, data=json.dumps(payload), headers=headers)

    if response.status_code == 204:
        print(f"✅ Registered {app_name} with Eureka!")
    else:
        print(f"❌ Failed to register with Eureka: {response.status_code} - {response.text}")