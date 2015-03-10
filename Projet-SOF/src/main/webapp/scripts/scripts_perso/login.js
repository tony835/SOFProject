$().ready(function() {
		// validate signup form on keyup and submit
		$("#signupForm").validate({
			rules: {
				login: {
					required: true,
					minlength: 1
				},
				password: {
					required: true,
					minlength: 1
				}
			},
			messages: {
				login: {
					required: "Please enter a login",
					minlength: "Your username must consist of at least 1 characters"
				},
				password: {
					required: "Please enter a password",
					minlength: "Your username must consist of at least 1 characters"
				}
			}
		});
	}
);