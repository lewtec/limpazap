#!/usr/bin/env python3
import sys
import zipfile


def manifest_bytes(path):
    data = b""
    with zipfile.ZipFile(path) as bundle:
        names = [name for name in bundle.namelist() if name.endswith("AndroidManifest.xml")]
        if not names:
            sys.exit(f"no manifest in {path}")
        for name in names:
            data += bundle.read(name)
    return data


def has_id(data, application_id):
    return application_id.encode() in data or application_id.encode("utf-16le") in data


fdroid_id = "com.lucao.limpazap"
play_id = "br.tec.lew.limpazap"
apk = manifest_bytes("build/app/outputs/flutter-apk/app-release.apk")
aab = manifest_bytes("build/app/outputs/bundle/release/app-release.aab")
if not has_id(apk, fdroid_id) or has_id(apk, play_id):
    sys.exit("APK must keep applicationId com.lucao.limpazap")
# Play id is the bundle applicationId. The namespace class stays com.lucao.limpazap.
if not has_id(aab, play_id) or not has_id(aab, fdroid_id):
    sys.exit("AAB must use applicationId br.tec.lew.limpazap")
