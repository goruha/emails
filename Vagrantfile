unless Vagrant.has_plugin?("vagrant-docker-compose")
  system("vagrant plugin install vagrant-docker-compose")
  puts "Dependencies installed, please try the command again."
  exit
end


Vagrant.configure(2) do |config|

  config.vm.define "emails.dev" do |adev|
    adev.vm.box = "ubuntu/trusty64"
    adev.vm.hostname = "emails.dev"
    adev.vm.network "private_network", ip: "10.0.0.81"
    adev.vm.synced_folder ".", "/vagrant"
    #adev.hostsupdater.aliases = ["athena.dev"]
  end

  config.vm.provider :virtualbox do |vb, override|
    vb.customize ["modifyvm", :id, "--memory", "1024"]
    vb.customize ["modifyvm", :id, "--cpus", "1"]
  end

  config.vm.provision "docker"

  config.vm.provision "docker_compose",
                      compose_version: "1.7.1",
                      rebuild: true,
                      run: "always",
                      yml: "/vagrant/docker-compose.yml"
end