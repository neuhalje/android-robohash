#!/usr/bin/env  bash


function error {
  printf "\e[31m$*\e[0m\n" >&2
}
function info {
  printf "\e[32m$*\e[0m\n"
}

if [ "x${DOCKER_MACHINE_NAME}" == "x" ]
then
        DOCKER_COMPOSE="docker-compose -f docker-compose.yml "
else
        DOCKER_COMPOSE="docker-compose -f docker-compose.fusion.yml -f docker-compose.yml "
fi

$DOCKER_COMPOSE build

if [ ! $? -eq 0 ];
then
ERR=$?
error "################################## build ERROR"
error "##"
error "## ERROR: Please check the build output for errors"
error "##"
exit $ERR
fi

$DOCKER_COMPOSE run build

if [ $? -eq 0 ];
then

info "################################## build complete"
info "##"
info "## Build output can be found in ./build"
info "##"
info "##  Lint report:  \e[1m file://$PWD/build/outputs/lint-results.html \e[21m"
info "##  Test report:  \e[1m file://$PWD/build/reports/tests/debug/index.html \e[21m"
info "##  AARs:  "
find ./build -type f -name \*aar | while read f; do info "##           - \e[1m$f\e[21m"; done
info "##"
else
error "################################## build ERROR"
error "##"
error "## ERROR: Please check the build output for errors"
error "##"
fi
