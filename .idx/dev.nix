{ pkgs, ... }: {
  # Install the required packages for your project.
  # The pkgs.docker package includes the modern 'docker compose' v2 plugin.
  packages = [ 
    pkgs.jdk17
    pkgs.maven
    pkgs.docker
  ];

  # Enable the Docker daemon so that Docker commands can be run
  services.docker.enable = true;
}
