import datetime
import random
import time

import paramiko

def my_func():
    hostname = "hadoop102"
    port = 22
    username = "root"
    password = "000429"
    client = paramiko.SSHClient()
    client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    client.connect(hostname, port, username, password, compress=True)
    sftp_client = client.open_sftp()
    # try:
    #     for line in remote_file:
    #         print(line)
    # finally:
    #     remote_file.close()
    # 获取系统时间
    num1 = 3000
    for i in range(1000):
        remote_file = sftp_client.open("/opt/module/data/test1.csv", 'a')  # 文件路径
        time1 = datetime.datetime.now()
        time1_str = datetime.datetime.strftime(time1, '%Y-%m-%d %H:%M:%S')
        print("当前时间：  " + time1_str)
        time.sleep(random.randint(1, 4))
        num1_str = str(num1 + random.randint(-1300, 1700))
        print("当前随机数：  " + num1_str)
        remote_file.write(time1_str + "," + num1_str + "\n")
        remote_file.close()

if __name__ == '__main__':
    my_func()