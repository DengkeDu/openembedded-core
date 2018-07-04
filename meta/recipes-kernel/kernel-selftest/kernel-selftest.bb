SUMMARY = "Kernel selftest for Linux"
DESCRIPTION = "Kernel selftest for Linux"
LICENSE = "GPLv2"

DEPENDS = " \
    elfutils \
    libcap \
    libcap-ng \
    fuse \
    util-linux \
    rsync-native \
"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

inherit linux-kernel-base kernel-arch

do_populate_lic[depends] += "virtual/kernel:do_patch"

inherit kernelsrc

S = "${WORKDIR}/${BP}"

# The LDFLAGS is required or some old kernels fails due missing
# symbols and this is preferred than requiring patches to every old
# supported kernel.
LDFLAGS="-ldl -lutil"

EXTRA_OEMAKE = '\
    CROSS_COMPILE=${TARGET_PREFIX} \
    ARCH=${ARCH} \
    CC="${CC}" \
    AR="${AR}" \
    LD="${LD}" \
    EXTRA_CFLAGS="-ldw" \
    ${PACKAGECONFIG_CONFARGS} \
'

EXTRA_OEMAKE += "\
    'DESTDIR=${D}' \
    'prefix=${prefix}' \
    'bindir=${bindir}' \
    'sharedir=${datadir}' \
    'sysconfdir=${sysconfdir}' \
    'sharedir=${@os.path.relpath(datadir, prefix)}' \
    'mandir=${@os.path.relpath(mandir, prefix)}' \
    'infodir=${@os.path.relpath(infodir, prefix)}' \
"

KERNEL_SELFTEST_SRC ?= "Makefile \
             include \
             tools \
"

# Add bpf selftest now, other can be added later.
do_compile () {
	# Linux kernel build system is expected to do the right thing
	unset CFLAGS
	oe_runmake -C ${S}/tools/testing/selftests/bpf
}

# On target, enter /opt/kselftest/bpf directory, run "./test_align 0 11"
# The test_align testcase test the bpf instruction set, the testcase defined here:
# https://git.kernel.org/pub/scm/linux/kernel/git/bpf/bpf.git/tree/tools/testing/selftests/bpf/test_align.c#n47
do_install () {
	# Linux kernel build system is expected to do the right thing
	unset CFLAGS
	mkdir -p ${D}/opt/kselftest/bpf
	install -m 0755 ${B}/tools/testing/selftests/bpf/test_align ${D}/opt/kselftest/bpf/
}

do_configure[prefuncs] += "copy_perf_source_from_kernel remove_clang_related"
python copy_perf_source_from_kernel() {
    sources = (d.getVar("KERNEL_SELFTEST_SRC") or "").split()
    src_dir = d.getVar("STAGING_KERNEL_DIR")
    dest_dir = d.getVar("S")
    bb.utils.mkdirhier(dest_dir)
    for s in sources:
        src = oe.path.join(src_dir, s)
        dest = oe.path.join(dest_dir, s)
        if os.path.isdir(src):
            oe.path.copytree(src, dest)
        else:
            bb.utils.copyfile(src, dest)
}

remove_clang_related() {
	sed -i -e '/test_pkt_access/d' -e '/test_pkt_md_access/d' ${S}/tools/testing/selftests/bpf/Makefile
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

INHIBIT_PACKAGE_DEBUG_SPLIT="1"
FILES_${PN} += "/opt/kselftest/bpf/*"
