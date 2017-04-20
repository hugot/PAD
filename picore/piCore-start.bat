qemu-system-arm -kernel zImage -cpu arm1176 -m 256 -M versatilepb -no-reboot -serial stdio -initrd piCore.gz -append "root=/dev/ram0 elevator=deadline rootwait quiet nortc nozswap noswap"
