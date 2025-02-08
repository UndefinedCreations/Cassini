package com.undefined.cassini.exception

class InvalidChestSizeException(size: Int) : Exception("Invalid inventory size provided! Expected 9, 18, 27, 36, 46 or 54. Got $size")