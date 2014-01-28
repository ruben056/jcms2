// The funky chain in that big return statement gets any loose text that's not in a tag
// of the current element, but since text() gets all the childrens' text too,
// we have to remove those.
// If cfloose is true, apply clearfix to parents all of whose children are floated,
// but there is also loose (non-contained, non-tagged) text as a child
// In order to apply clearfix, we find each element and the following must be true:
// 1. It must be floated
// 2. Its siblings must all be floated
// 3a. If cfloose is true, then that's it!
// 3b. If cfloose is false, then there must be no "loose" text in
//     that tag in order for its parent to have clearfix.  
jQuery.fn.autoClearfix = function(cfloose) {
//    var candidates = [];
    jQuery("*", jQuery(this)).filter(function() {
        return jQuery(this).css("float") != "none" && jQuery(this).siblings().length == jQuery(this).siblings().filter(function() {
            return jQuery(this).css("float") != "none";
        }).length && (cfloose ? true : jQuery(this).parent().clone().children().remove().end().text().replace(/(\n|\s)/gm, '') === "");
    }).parent().each(function() {
        jQuery(this).addClass("clearfix");
    });
};

// Usage
jQuery(document).ready(function() {
	// is now done in generated code because variable arguments
//	var myMenu = new MenuMatic({ id:'nav' }); 
	jQuery("#menuWrapper").autoClearfix(true);
});