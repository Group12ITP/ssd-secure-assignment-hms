<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doctor Registration</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #005bff, #ffffff);
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .register-container {
            background: #fff;
            display: flex;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
            overflow: hidden;
            width: 1300px;
            max-width: 95%;
            height: 90vh;
        }

        .right-panel {
            flex: 1;
            position: relative;
            overflow: hidden;
            color: #fff;
            display: flex;
            flex-direction: column;
            justify-content: flex-end;
            align-items: flex-start;
            text-align: left;
        }

        .carousel {
            position: absolute;
            top: 0; left: 0;
            width: 100%; height: 100%;
            z-index: 0;
        }

        .carousel img {
            position: absolute;
            width: 100%; height: 100%;
            object-fit: cover;
            opacity: 0;
            transition: opacity 1s ease-in-out;
        }

        .carousel img.active {
            opacity: 1;
        }

        .overlay {
            position: absolute;
            top: 0; left: 0;
            width: 100%; height: 100%;
            background: linear-gradient(135deg, rgba(106,17,203,0.46), rgba(37,117,252,0.44));
            z-index: 1;
        }

        .right-content {
            position: relative;
            z-index: 2;
            padding: 60px 40px;
        }

        .right-content h2 {
            font-size: 32px;
            font-weight: 700;
            margin-bottom: 15px;
        }
        .h2head{
            color: #ffffff;
        }

        .right-content span {
            color: #ffeb3b;
        }

        .right-content p {
            font-size: 14px;
            opacity: 0.9;
        }

        .left-panel {
            flex: 1.5;
            padding: 30px 50px;
            overflow-y: auto;
        }

        h3.section-title {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 10px;
            color: #333;
            border-left: 4px solid #2575fc;
            padding-left: 10px;
        }

        .card {
            background: #f9fafc;
            border-radius: 12px;
            padding: 20px 25px;
            margin-bottom: 25px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 15px;
        }

        label {
            font-size: 13px;
            font-weight: 500;
            color: #555;
        }

        input, select {
            width: 90%;
            padding: 10px 12px;
            border-radius: 8px;
            border: 1px solid #ccc;
            margin-top: 6px;
            outline: none;
            transition: 0.3s;
            background-color: #fff;
            appearance: none;
        }

        input:focus, select:focus {
            border-color: #2575fc;
            box-shadow: 0 0 5px rgba(37,117,252,0.3);
        }

        select {
            background-image: url("data:image/svg+xml;utf8,<svg fill='%232575fc' height='24' viewBox='0 0 24 24' width='24' xmlns='http://www.w3.org/2000/svg'><path d='M7 10l5 5 5-5z'/></svg>");
            background-repeat: no-repeat;
            background-position: right 12px center;
            background-size: 16px;
            padding-right: 32px;
        }

        .register-btn {
            width: 100%;
            padding: 14px;
            border: none;
            border-radius: 8px;
            background: linear-gradient(135deg, #2575fc, #6a11cb);
            color: #fff;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: 0.3s;
        }

        .register-btn:hover {
            opacity: 0.9;
        }

        @media (max-width: 900px) {
            .register-container {
                flex-direction: column-reverse;
                height: auto;
            }
            .right-panel {
                height: 250px;
            }
        }
        .alert {
            padding: 12px 15px;
            border-radius: 6px;
            margin-bottom: 20px;
        }
        .alert-error { border: 1px solid #e74c3c; color: #e74c3c; background: rgba(231,76,60,0.08); }
        .alert-success { border: 1px solid #2ecc71; color: #2ecc71; background: rgba(46,204,113,0.08); }
    </style>
</head>
<body>

<div class="register-container">
    <div class="left-panel">
        <h2 style="text-align:center; font-weight:800; color:#2575fc; margin-bottom:20px;">Doctor Registration</h2>

        <form th:action="@{/doctor/register}" method="post" th:object="${doctor}">

            <div th:if="${error}" class="alert alert-error" th:text="${error}"></div>
            <div th:if="${success}" class="alert alert-success" th:text="${success}"></div>

            <div class="card">
                <h3 class="section-title">Personal Information</h3>
                <div class="form-grid">
                    <div>
                        <label>Full Name</label>
                        <input type="text" th:field="*{fullName}" required>
                    </div>
                    <div>
                        <label>NIC</label>
                        <input type="text" th:field="*{nic}" required>
                    </div>
                    <div>
                        <label>Date of Birth</label>
                        <input type="date" th:field="*{dateOfBirth}" required>
                    </div>
                    <div>
                        <label>Gender</label>
                        <select th:field="*{gender}" required>
                            <option value="" disabled selected>Select Gender</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </select>
                    </div>
                    <div>
                        <label>Address</label>
                        <input type="text" th:field="*{address}">
                    </div>
                    <div>
                        <label>Contact Number</label>
                        <input type="text" th:field="*{contactNumber}">
                    </div>
                    <div>
                        <label>Email</label>
                        <input type="email" th:field="*{email}">
                    </div>
                </div>
            </div>

            <div class="card">
                <h3 class="section-title">Professional Information</h3>
                <div class="form-grid">
                    <div>
                        <label>License Number</label>
                        <input type="text" th:field="*{licenseNumber}" required>
                    </div>
                    <div>
                        <label>Qualifications</label>
                        <input type="text" th:field="*{qualifications}">
                    </div>
                    <div>
                        <label>Specialization</label>
                        <input type="text" th:field="*{specialization}" required>
                    </div>
                    <div>
                        <label>Department</label>
                        <input type="text" th:field="*{department}">
                    </div>
                    <div>
                        <label>Years of Experience</label>
                        <input type="number" th:field="*{yearsOfExperience}" min="0">
                    </div>
                    <div>
                        <label>Working Days</label>
                        <input type="text" th:field="*{workingDays}" placeholder="Mon-Fri">
                    </div>
                    <div>
                        <label>Working Hours</label>
                        <input type="text" th:field="*{workingHours}" placeholder="9:00 AM - 5:00 PM">
                    </div>
                </div>
            </div>

            <div class="card">
                <h3 class="section-title">Account Security</h3>
                <div class="form-grid">
                    <div>
                        <label>Username</label>
                        <input type="text" th:field="*{username}" required>
                    </div>
                    <div>
                        <label>Password</label>
                        <input type="password" th:field="*{password}" required>
                    </div>
                    <div>
                        <label>Security Question</label>
                        <input type="text" th:field="*{securityQuestion}">
                    </div>
                    <div>
                        <label>Security Answer</label>
                        <input type="text" th:field="*{securityAnswer}">
                    </div>
                </div>
            </div>

            <button type="submit" class="register-btn">Register</button>
        </form>
    </div>

    <div class="right-panel">
        <div class="carousel">
            <img src="https://images.unsplash.com/photo-1584467735871-8e85353a8413?auto=format&fit=crop&w=800&q=80" class="active" alt="Doctor 1">
            <img src="https://images.unsplash.com/photo-1574706473454-a3b3da3fc5b5?auto=format&fit=crop&w=800&q=80" alt="Doctor 2">
            <img src="https://images.unsplash.com/photo-1526256262350-7da7584cf5eb?auto=format&fit=crop&w=800&q=80" alt="Doctor 3">
        </div>
        <div class="overlay"></div>

        <div class="right-content">
            <h2 class="h2head">Join <span>Our Doctor Portal</span></h2>
            <p>Register to manage patients, appointments, and schedules efficiently and securely.</p>
        </div>
    </div>
</div>

<script>
    const imgs = document.querySelectorAll('.carousel img');
    let idx = 0;
    setInterval(() => {
        imgs[idx].classList.remove('active');
        idx = (idx + 1) % imgs.length;
        imgs[idx].classList.add('active');
    }, 4000);
</script>
</body>
</html>