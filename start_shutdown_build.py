import sys
import os

path = sys.argv[0]
jar_name = sys.argv[1]

port = 8000
ip = 'camsyn.cn'
if len(sys.argv[2])>2:
    ip = sys.argv[2]
if len(sys.argv) == 4:
    port = int(sys.argv[3])

if not jar_name:
    print('please enter your jar name')

shutdown = f'''pid=`ps ax | grep -i {jar_name} |grep java | grep -v grep | awk '{{print $1}}'`
if [ -z "$pid" ] ; then
    echo "No app running."
    exit -1;
fi
kill ${{pid}}
echo "shutdown successfully"\n'''

startup = f'''nohup java -jar -Dfile.encoding=utf-8 {jar_name} &
echo "boot successfully"\n'''

restart = f'''cat nohup.out >> log_backup.log
echo "" > nohup.out
sh ./shutdown.sh && sh ./startup.sh\n'''

ip_registry = f'''spring.cloud.nacos.discovery.ip = {ip}\n
server.port = {port}\n'''


with open('./shutdown.sh', mode='w+') as f:
    f.write(shutdown)

with open('./startup.sh', mode='w+') as f:
    f.write(startup)

with open('./restart.sh', mode='w+') as f:
    f.write(restart)

with open('./application.properties', mode='w+') as f:
    f.write(ip_registry)

os.system('chmod 777 shutdown.sh')
os.system('chmod 777 startup.sh')
os.system('chmod 777 restart.sh')
