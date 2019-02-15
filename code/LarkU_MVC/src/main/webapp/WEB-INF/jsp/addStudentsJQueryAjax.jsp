<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>LarkU_SpringMVC</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/schoolStyles.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.8.2.js"></script>

<script language="javascript">
	var RegExes = {
		"id" : "^[2-9].*",
		"name" : "^[A-Z].*",
		"phoneNumber" : "^[0-9]{3}[\\s\\-][0-9]{3}[\\s\\-][0-9]{4}$"
	};

	var validate = function(elem) {
		var name = elem["name"];
		var re = RegExes[name];

		var value = checkElem2(elem, re);
	}

	var doAdd = function(event) {
		event = event ? event : window.event;
		//console.log(event.clientX);
		var allGood = true;
		var name, id, phoneNumber;
		//Validation REs
		var idRe = "^[2-9].*";
		var nameRe = "^[A-Z].*";
		var phoneRe = "^[0-9]{3}[\\s\\-][0-9]{3}[\\s\\-][0-9]{4}$";

		var name = checkElem2($('#nameInput'), nameRe);
		var phoneNumber = checkElem2($('#phoneNumberInput'), phoneRe);
		var status = $('#status').val();

		if (!name || !phoneNumber) {
			allGood = false;
		}

		if (allGood) {
			//Set up the JSON object to send in the request
			var newCourse = {
				"id" : id,
				"name" : name,
				"phoneNumber" : phoneNumber,
				"status" : status
			}

			$.ajax({
				//url : "http://localhost:8080/Solutions/jaxrs/jpa/addStudent",
				//url : "http://localhost:8080/${pageContext.request.contextPath}/adminrest/student",
				url : "${pageContext.request.contextPath}/adminrest/student",
				type : "post",
				dataType : "json",
				data : newCourse,
				data : JSON.stringify(newCourse),
				contentType : "application/json",
				processData : false,
				success : function(data) {
					//console.log("data = " + data);
					//data = data.resultObject;
					//Add it to the list of students
					var e = $('#studentList');
					e.append($('<li>').append(data.name + " - " + data.id + " - " + data.phoneNumber));

				},

				error : function(jqXHR, textStatus, errorThrown) {
					alert("textStatus is " + textStatus + ", error is " + errorThrown);
				}
			});
		}

	}

	function checkElem(elem, validRe) {
		var result = null;
		if (!elem.val().match(validRe)) {
			console.log("bad " + elem.attr("name"));
			elem.css("color", "red");
		} else {
			elem.css("color", "blue");
			result = elem.val();
		}

		return result;
	}

	function checkElem2(elem, validRe) {
		var result = null;

		if (!$(elem).val().match(validRe)) {
			console.log("bad " + $(elem).attr("name"));
			$(elem).addClass("error");
		} else {
			$(elem).removeClass("error");
			result = $(elem).val();
		}

		return result;
	}

	$(document).ready(function() {
		//Fetch the set of current students and populate the list
		//var href = "http://localhost:8080/Solutions/jaxrs/students";
		//var href = "http://localhost:8080/${pageContext.request.contextPath}/adminrest/student/heldstudents";
		var href = "${pageContext.request.contextPath}/adminrest/student/heldstudents";
		$.get(href, function(values) {
			//var students = values;
			var students = values.students;
			//console.log("data = " + data);
			var e = $('#studentList');
			$.each(students, (function(index, data) {
				e.append($('<li>').append(data.name + " - " + data.id + " - " + data.phoneNumber));
			}))
		}, "json").error(function(jqXHR, textStatus, errorThrown) {
			alert("Error in Fetching Students: textStatus is " + textStatus + ", error is " + errorThrown);
		});
	});
</script>
</head>
<body>
	<form id="myForm" action="someAction" method="post">
		<h2>Student Info</h2>
		<div class="table">
			<div class="tableRow">
				<label for="name" class="tableCell">Name</label> <input
					id="nameInput" class="tableCell" type="text" name="name"
					onkeyup="validate(this)" />
			</div>
			<div class="tableRow">
				<label for="phoneNumber" class="tableCell">Phone Number</label> <input
					id="phoneNumberInput" class="tableCell" type="text"
					name="phoneNumber" onkeyup="validate(this)" />
			</div>
			<div class="tableRow">
				<label for="status" class="tableCell">Status</label> <select
					name="status" id="status" class="tableCell">
					<c:forEach items="${statusList}" var="status">
						<option value="${status}">${status}</option>
					</c:forEach>
				</select>
			</div>
			<div class="tableRow">
				<div class="tableCell">
					<input type="button" value="Add" onClick="doAdd(event)" />
				</div>
			</div>
		</div>
	</form>
	<br />
	<div id="listDiv">
		<h2>List of Students Added</h2>
		<ol id="studentList">
		</ol>
	</div>
	<div>
		<br /> <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
	</div>
</body>
</html>