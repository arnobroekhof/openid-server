/**
 * Created on 2008-10-18 18:49:23
 */
/**
 * Generate a random string.
 * 
 * @return a random string
 */
function nextRandomId() {
	var g = "", i;
	for (i = 0; i < 32; i++) {
		g += Math.floor(Math.random() * 0xF).toString(0xF) +
			(i == 8 || i == 12 || i == 16 || i == 20 ? "-" : "");
	}
	g += "-" + new Date().getTime();
	return g.toUpperCase();
}

/**
 * Add an attribute row.
 * 
 * @param handler
 *            the add action handler
 */
function personaAddAttribute(handler) {
	var attribute = $(handler).parent().parent(),
		newAttribute = attribute.clone(),
		key = nextRandomId(),
		attributeId = $("input[name^='attribute.id']", newAttribute),
		attributeAlias = $("input[name^='attribute.alias']", newAttribute),
		attributeType = $("input[name^='attribute.type']", newAttribute),
		attributeValue = $("input[name^='attribute.value']", newAttribute);

	attributeId.attr("name", "attribute.id" + key);
	attributeAlias.attr("name", "attribute.alias." + key);
	attributeType.attr("name", "attribute.type." + key);
	attributeValue.attr("name", "attribute.value." + key);

	attributeId.val("");
	attributeAlias.val("");
	attributeType.val("");
	attributeValue.val("");

	attribute.after(newAttribute);
}

/**
 * Remove the attribute.
 * 
 * @param handler
 *            the remove action handler
 */
function personaRemoveAttribute(handler) {
	var attribute = $(handler).parent().parent();
	attribute.remove();
}

/**
 * Add an attribute value.
 * 
 * @param handler
 *            the add action handler
 */
function personaAddAttributeValue(handler) {
	var value = $(handler).parent(),
		newValue = value.clone();
	$("input", newValue).val("");
	value.after(newValue);
}

/**
 * Remove the attribute value.
 * 
 * @param handler
 *            the remove action handler
 */
function personaRemoveAttributeValue(handler) {
	var value = $(handler).parent();
	value.remove();
}
