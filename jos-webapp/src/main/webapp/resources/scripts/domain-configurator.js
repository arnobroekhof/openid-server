var TYPE_SUBDOMAIN = 1;
var TYPE_SUBDIRECTORY = 2;

/**
 * Get the identifer type from the form.
 * 
 * @return identifier type
 */
function getType() {
	var v;
	if (document.getElementById("type_" + TYPE_SUBDOMAIN).checked) {
		v = TYPE_SUBDOMAIN;
	} else if (document.getElementById("type_" + TYPE_SUBDIRECTORY).checked) {
		v = TYPE_SUBDIRECTORY;
	}
	return v;
}

/**
 * Get appended port if it is not default port of the protocol.
 * 
 * @return port block to append
 */
function getAppendedPort() {
	var v = document.location.port;
	if (v !== "") {
		return ":" + v;
	} else {
		return "";
	}
}

/**
 * Get appended context path.
 * 
 * @return the context path
 */
function getAppendedContextPath() {
	return document.location.pathname.replace("/domain-configurator", "");
}

/**
 * Get appended member path if exists.
 * 
 * @return member path block to append
 */
function getAppendedMemberPath() {
	var v = document.getElementById("memberPath").value;
	if (v !== "") {
		return v + "/";
	} else {
		return "";
	}
}

/**
 * Get identifier prefix.
 * 
 * @return identifier prefix
 */
function getIdentifierPrefix() {
	var p = "";

	switch (getType()) {
	case TYPE_SUBDOMAIN:
		p += document.location.protocol + "//";
		break;
	case TYPE_SUBDIRECTORY:
		p += document.location.protocol + "//";
		p += document.getElementById("name").value;
		p += getAppendedPort();
		p += getAppendedContextPath() + "/"
		p += getAppendedMemberPath();
		break;
	}

	return p;
}

/**
 * Get identifier suffix.
 * 
 * @return identifier suffix
 */
function getIdentifierSuffix() {
	var s = "";

	switch (getType()) {
	case TYPE_SUBDOMAIN:
		s += "." + document.getElementById("name").value;
		s += getAppendedPort();
		s += document.location.pathname.replace("domain-configurator", "");
		s += getAppendedMemberPath();
		break;
	case TYPE_SUBDIRECTORY:
		break;
	}

	return s;
}

/**
 * Show identifier pattern.
 */
function showIdentifierPattern() {
	document.getElementById("openid_identifier_pattern").innerHTML = getIdentifierPrefix()
			+ "<span class='username'>username</span>" + getIdentifierSuffix();
}
