sudo apt install ./clamav-0.104.2.linux.x86_64.deb
sudo groupadd clamav
sudo useradd -g clamav -s /bin/false -c "Clam Antivirus" clamav
sudo chown -R clamav:clamav /usr/local/share/clamav
