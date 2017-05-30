#!/bin/sh
qemu-system-arm -kernel piCore-140513-QEMU -cpu arm1176 -m 256 -M versatilepb \
-serial stdio -initrd piCore-140519-QEMU.gz -append \
"root=/dev/ram0 elevator=deadline rootwait quiet nortc nozswap"
