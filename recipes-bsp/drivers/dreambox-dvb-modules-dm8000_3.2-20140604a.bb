SUMMARY = "Hardware drivers for Dreambox"
SECTION = "base"
LICENSE = "CLOSED"
DEPENDS += "virtual/kernel"
PRIORITY = "required"
PR = "r7.0"

COMPATIBLE_MACHINE = "dm8000"

SRC_URI = " \
			file://dreambox-dvb-modules-dm8000-3.2-dm8000-20140604a.tar.bz2 \
			file://modules \
"

inherit module-base

do_compile() {
}

do_install() {
	install -d ${D}${sysconfdir}/modules-load.d
	install -m 0644 ${WORKDIR}/modules ${D}${sysconfdir}/modules-load.d/${PN}.conf
	install -d ${D}/lib/modules/${DM_LOCALVERSION}/extra
	install -m 0644 ${WORKDIR}/LICENSE ${D}/lib/modules/${DM_LOCALVERSION}/extra
	install -m 0644 ${WORKDIR}/*.ko ${D}/lib/modules/${DM_LOCALVERSION}/extra
}

PACKAGES = "${PN}"

RDEPENDS:${PN} += "dreambox-secondstage-${MACHINE} kernel-${DM_LOCALVERSION}"

# We don't use KERNEL_VERSION in this recipe, because the
# precompiled modules depend on a specific version.
DM_LOCALVERSION = "${@'-'.join('${PV}'.split('-')[:-1])}-${MACHINE}"
DRIVERDATE = "${@'${PV}'.split('-')[-1]}"

FILESEXTRAPATHS:prepend := "${THISDIR}/dreambox-dvb-modules:"

FILES:${PN} += "${sysconfdir}/modules-load.d/${PN}.conf /lib/modules/${DM_LOCALVERSION}/extra"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
