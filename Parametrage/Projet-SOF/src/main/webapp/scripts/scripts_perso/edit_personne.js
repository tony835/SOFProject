$()
.ready(
		function() {
			jQuery.validator.addMethod("dateUS", function(value,
					element) {
				var check = false;
				var re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
				if (re.test(value)) {
					var adata = value.split('/');
					var dd = parseInt(adata[0], 10); // was mm (mese / month)
					var mm = parseInt(adata[1], 10); // was gg (giorno / day)
					var yyyy = parseInt(adata[2], 10); // was aaaa (anno / year)
					var xdata = new Date(yyyy, mm - 1, dd);
					if ((xdata.getFullYear() == yyyy)
							&& (xdata.getMonth() == mm - 1)
							&& (xdata.getDate() == dd)) {
						check = true;
						todaysDate = new Date();
						todaysDate.setHours(0, 0, 0, 0);
						if (xdata <= todaysDate)
							check = true;
						else
							check = false;

					} else
						check = false;
				} else
					check = false;
				return this.optional(element) || check;
			});

			$()
			.ready(
					function() {
						jQuery.validator
						.addMethod(
								"minLenght_",
								function(value,
										element) {
									return value.length > 5;
								});
						jQuery.validator
						.addMethod(
								"maxLenght_",
								function(value,
										element) {
									return value.length < 32;
								});
						// validate signup form on keyup and submit
						$("#signupForm")
						.validate(
								{
									rules : {
										nom : {
											required : true
										},
										prenom : {
											required : true
										},
										email : {
											required : true,
											email : true
										},
										siteWEB : {
											required : true,
											url : true
										},
										dateNais : {
											required : true,
											dateUS : true
										},
										username : {
											required : true,
											minlength : 5,
											maxlength : 32
										},
										password : {
											required : true,
											minlength : 5,
											maxlength : 32
										},
										repeatPassword : {
											required : true,
											equalTo : "#password"
										}
									},

									messages : {
										nom : {
											required : "Please enter your surname"
										},
										prenom : {
											required : "Please enter your name"
										},
										email : {
											required : "Please enter a email",
											email : "Please enter a valid email"
										},
										siteWEB : {
											required : "Please enter your website",
											url : "Please enter a valid url"
										},
										dateNais : {
											required : "Please enter your birstday like (d/m/yyyy)",
											dateUS : "Please enter a valid date (d/m/yyyy) and less than today"
										},
										username : {
											required : "Please enter a username",
											minlength : "Your username must consist of at least 5 characters",
											maxlength : "Your username must consist of at most 32 characters"
										},
										password : {
											required : "Please enter a password",
											minlength : "Your username must consist of at least 5 characters",
											maxlength : "Your username must consist of at most 32 characters"
										},
										repeatPassword : {
											required : "Please enter the samme passwrd",
											equalTo : "Please enter the same password as above"
										}
									}
								});

					});
		}
);