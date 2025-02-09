package com.undefined.cassini.exception

class InvalidPageException(currentPage: Int) : Exception("$currentPage is an invalid page!")