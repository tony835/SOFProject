// Select the main list and add the class "hasSubmenu" in each LI that contains an UL
$('ul').each(function(){
  $this = $(this);
  $this.find("li").has("ul").addClass("hasSubmenu");
});
// Find the last li in each level
$('li:last-child').each(function(){
  $this = $(this);
  // Check if LI has children
  if ($this.children('ul').length === 0){
    // Add border-left in every UL where the last LI has not children
    $this.closest('ul').css("border-left", "1px solid gray");
    //$this.children("i").toggle();
  } else {
    // Add border in child LI, except in the last one
    $this.closest('ul').children("li").not(":last").css("border-left","1px solid gray");
    // Add the class "addBorderBefore" to create the pseudo-element :defore in the last li
    $this.closest('ul').children("li").last().children("a").addClass("addBorderBefore");
    // Add margin in the first level of the list
    $this.closest('ul').css("margin-top","20px");
    // Add margin in other levels of the list
    $this.closest('ul').find("li").children("ul").css("margin-top","20px");
  };
});
// Add bold in li and levels above
$('ul li').each(function(){
  $this = $(this);
  $this.mouseenter(function(){
    $( this ).children("a").css({"font-weight":"bold","color":"#336b9b"});
  });
  $this.mouseleave(function(){
    $( this ).children("a").css({"font-weight":"normal","color":"#428bca"});
  });
});
// Add button to expand and condense - Using FontAwesome
$('ul li.hasSubmenu').each(function(){
  $this = $(this);
  $this.prepend("<a href='#'><b>o</b></a>");
  $this.children("a").not(":last").removeClass().addClass("toogle");


});
// Actions to expand and consense
$('ul li.hasSubmenu a.toogle').click(function(){
  $this = $(this);
  $this.closest("li").children("ul").toggle("slow");
  //$this.children("i").toggle();
  return false;
});

// ferme les noeuds de tous les objets
$('ul li.hasSubmenu').each(function(){
	  $this = $(this);
	  $this.closest("li").children("ul").toggle();
	 // $this.children("i").toggle();
	});

//ouvre un objet quand il vient d'être modifié
$(window).load(function() {
//	var url = window.location.search;
//	var name ="";
	name =  window.location.hash.substring(1);
//	var t = location.search.substring(1).split('&');
//	for (var i=0; i<t.length; i++){
//		var x = t[ i ].split('=');
//		if(x[0] == "modifiedO"){
//			name = x[1];
//			break;
//		}			
//	}
	if(name != ""){
		$li = $(document.getElementsByName(name)); //TODO simplifier en ne prenant que les name des <li>
		$li.children("ul").toggle();
		$li.parentsUntil($("#list"),"ul").toggle();

		//TODO toggle du "i", pour l'image + -> -

		var aTag = $("a[id='"+ name +"']");
		$('html,body').animate({scrollTop: aTag.offset().top}, 1);

	}	
});
