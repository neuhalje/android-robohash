#!/usr/bin/env  bash


function error {
  printf "\e[31m$*\e[0m\n" >&2
}
function info {
  printf "\e[32m$*\e[0m\n"
}

DOCKER_IMAGE=neuhalje/build_robohash:1.0

# Build without context
docker build  -t $DOCKER_IMAGE - < Dockerfile

ERR=$?
if [ ! $ERR -eq 0 ];
then
error "################################## docker build ERROR"
error "##"
error "## ERROR: Please check the *docker build* output for errors"
error "##"
exit $ERR
fi


if [ "x${DOCKER_MACHINE_NAME}" == "x" ]
then
        DOCKER_MOUNT="-v ${PWD}:/root/robohash"
else
        DOCKER_MOUNT="-v /mnt/hgfs/${PWD}:/root/robohash"
fi

docker run $DOCKER_MOUNT -w /root/robohash $DOCKER_IMAGE sh -c './gradlew clean && ./gradlew build'


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
info "## Inspect image by running"
info "##   \e[1m docker run -ti $DOCKER_MOUNT -w /root/robohash $DOCKER_IMAGE /bin/bash \e[21m"
else
error "################################## build ERROR"
error "##"
error "## ERROR: Please check the build output for errors"
error "##"
info "## Inspect image by running"
info "##   \e[1m docker run -ti $DOCKER_MOUNT -w /root/robohash $DOCKER_IMAGE /bin/bash \e[21m"
fi
