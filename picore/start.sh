#!/bin/sh
if [ ! -e /usr/local/tce.installed/qemu-arm ]; then tce-load -i qemu-arm; fi
qemu-system-arm -kernel zImage -cpu arm1176 -m 256 -M versatilepb -no-reboot \
-serial stdio -initrd piCore.gz -append \
"root=/dev/ram0 elevator=deadline rootwait quiet nozswap nortc noswap"
