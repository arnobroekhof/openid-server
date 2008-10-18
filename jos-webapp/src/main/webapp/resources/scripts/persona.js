/**
 * Created on 2008-10-18 18:49:23
 */
/**
 * Generate a random string.
 * 
 * @return a random string
 */
function nextRandomId() {
	var g = "";
	for ( var i = 0; i < 32; i++)
		g += Math.floor(Math.random() * 0xF).toString(0xF)
				+ (i == 8 || i == 12 || i == 16 || i == 20 ? "-" : "");
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
	var attribute = $(handler).parent().parent();
	var newAttribute = attribute.clone();
	var id = nextRandomId();
	$("input[name^='alias']", newAttribute).attr("name", "alias." + id);
	$("input[name^='type']", newAttribute).attr("name", "type." + id);
	$("input[name^='value']", newAttribute).attr("name", "value." + id);
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
	var value = $(handler).parent();
	value.after(value.clone());
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
