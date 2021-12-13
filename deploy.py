import os
import re
import subprocess
import sys

deploy_map = {
    "chat": "ali"
    , "file": "lmh"
    , "auth": "tx"
    , "gateway": "ali"
    , "request": "lmh"
    # , "order": "ali"
    # , "review": "ali"
}

base = './'
script_file_path = f'''./start_shutdown_build.py'''
base_dir = '~/workspace/OOAD/'
jar_dir = '/target/'
exec_jar_rgx = r'.*-exec.jar'
command = [
#     'C:/Users/camsyn/AppData/Local/JetBrains/Toolbox/apps/IDEA-U/ch-0/212.5284.40/plugins/maven/lib/maven3/bin/mvn',
    'mvn',
    'install'
    ]


def scp(service, file, remote):
    print(f'scp {file} {remote}:' + base_dir + f'{service}/')
    os.system(f'scp {file} {remote}:' + base_dir + f'{service}/')


def scp_script(service, remote):
    print(f'scp {script_file_path} {remote}:' + base_dir + f'{service}/')
    os.system(f'scp {script_file_path} {remote}:' + base_dir + f'{service}/')


def one_scp_job(service, remote):
    jar_path = get_jar_path(service)
    scp(service, jar_path, remote)
    scp_script(service, remote)


def get_jar_path(service):
    for dir in os.listdir(base + service + jar_dir):
        if re.match(exec_jar_rgx, dir):
            return f'{base}{service}/target/{dir}'
    return ''


def one_mvn_job(service):
    base_ = base + 'commons' + '/'
    ret = subprocess.run(command, shell=True, stdout=subprocess.PIPE,
                         stderr=subprocess.PIPE, cwd=base_)
    if ret.returncode == 0:
        print(f'success: {ret}')
    else:
        print(f'error: {ret}')
        print(ret.stderr.decode('gbk'))

    base_ = base + service + '/'
    ret = subprocess.run(command, shell=True, stdout=subprocess.PIPE,
                         stderr=subprocess.PIPE, cwd=base_)
    if ret.returncode == 0:
        print(f'success: {ret}')
    else:
        print(f'error: {ret}')
        print(ret.stderr.decode('gbk'))


def mvn_install_all():
    base_ = base
    ret = subprocess.run(command, shell=True, stdout=subprocess.PIPE,
                         stderr=subprocess.PIPE, cwd=base_)
    if ret.returncode == 0:
        print(f'success: {ret}')
    else:
        print(f'error: {ret}')
    print(ret.stderr.decode('gbk'))

if __name__ == '__main__':
    # deploy single service
    if len(sys.argv) > 1:
        service = sys.argv[1]
        remote = deploy_map[service] if len(sys.argv) == 2 else sys.argv[2]
        one_mvn_job(service)
        one_scp_job(service, remote)
    else:
        mvn_install_all()
        for service, remote in deploy_map.items():
            one_scp_job(service, remote)
