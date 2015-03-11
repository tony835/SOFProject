$().ready(function() {
		// validate signup form on keyup and submit
		$("#signupForm").validate({
			rules: {
				code: {
					required: true,
					minlength: 1
				},
				name: {
					required: true,
					minlength: 1
				}
			},
			messages: {
				code: {
					required: "Please enter a code",
					minlength: "Your code must consist of at least 1 characters"
				},
				name: {
					required: "Please enter a name",
					minlength: "Your name must consist of at least 1 characters"
				}
			}
		});
	}
);