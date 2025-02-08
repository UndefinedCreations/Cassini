package com.undefined.cassini.exception

class UnsupportedVersionException(supportedVersions: Collection<String>) : Exception("This minecraft version is unsupported! Supported versions: $supportedVersions")