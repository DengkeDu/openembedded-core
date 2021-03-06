LICENSE = "GPLv2 & GPLv3 & LGPLv2 & LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552 \
		    file://COPYING3;md5=d32239bcb673463ab874e80d47fae504 \
		    file://COPYING3.LIB;md5=6a6a8e020838b23406c81b19c1d46df6 \
		    file://COPYING.LIB;md5=9f604d8a4f8e74f4f5140845a21b6674"

SRC_URI = "http://ftp.gnu.org/gnu/gdb/gdb-${PV}.tar.xz \
           file://0001-include-sys-types.h-for-mode_t.patch \
           file://0002-make-man-install-relative-to-DESTDIR.patch \
           file://0003-mips-linux-nat-Define-_ABIO32-if-not-defined.patch \
           file://0004-ppc-ptrace-Define-pt_regs-uapi_pt_regs-on-GLIBC-syst.patch \
           file://0005-Add-support-for-Renesas-SH-sh4-architecture.patch \
           file://0006-Dont-disable-libreadline.a-when-using-disable-static.patch \
           file://0007-use-asm-sgidefs.h.patch \
           file://0008-Use-exorted-definitions-of-SIGRTMIN.patch \
           file://0009-Change-order-of-CFLAGS.patch \
           file://0010-resolve-restrict-keyword-conflict.patch \
           file://package_devel_gdb_patches_120-sigprocmask-invalid-call.patch \
           file://gdb-Fix-ia64-defining-TRAP_HWBKPT-before-including-g.patch \
"
SRC_URI[md5sum] = "f46487561f9a16916a8102316f7fd105"
SRC_URI[sha256sum] = "af61a0263858e69c5dce51eab26662ff3d2ad9aa68da9583e8143b5426be4b34"

