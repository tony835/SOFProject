$().ready(function() {
	// validate signup form on keyup and submit
	$("#signupForm").validate({
		rules: {
			nom: {
				required: true,
				minlength: 1
			}
		},
		messages: {
			nom: {
				required: "Please enter a username",
				minlength: "Your username must consist of at least 2 characters"
			}
		}
	});
});