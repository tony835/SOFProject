$().ready(function() {
		// validate signup form on keyup and submit
		$("#signupForm").validate({
			rules: {
				username: {
					required: true,
					minlength: 5,
					maxlength: 32
				},
				password: {
					required: true,
					minlength: 5,
					maxlength: 32
				}
			},
			messages: {
				username: {
					required: "Please enter a username",
					minlength: "Your username must consist of at least 5 characters",
					maxlength: "Your username must consist of at most 32 characters"
				},
				password: {
					required: "Please enter a password",
					minlength: "Your username must consist of at least 5 characters",
					maxlength: "Your username must consist of at most 32 characters"
				}
			}
		});
	}
);