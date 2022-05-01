sudo apt install ./clamav-0.104.2.linux.x86_64.deb -y
sudo groupadd clamav
sudo useradd -g clamav -s /bin/false -c "Clam Antivirus" clamav
sudo cp freshclam.conf /usr/local/etc/freshclam.conf
sudo freshclam
sudo chown -R clamav:clamav /usr/local/share/clamav
