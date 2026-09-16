<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <title>Pharmacist Login</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <style>
        body { margin: 0; font-family: 'Inter', sans-serif; background: linear-gradient(135deg, #005bff, #ffffff); display: flex; align-items: center; justify-content: center; height: 100vh; }
        .login-container { background: #fff; display: flex; border-radius: 18px; box-shadow: 0 12px 40px rgba(0, 0, 0, 0.12); overflow: hidden; width: 860px; max-width: 95%; transition: all 0.3s ease-in-out; }
        .left-panel { flex: 1; position: relative; overflow: hidden; color: #fff; display: flex; flex-direction: column; justify-content: flex-end; text-align: left; }
        .carousel { position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 0; }
        .carousel img { position: absolute; width: 100%; height: 100%; object-fit: cover; opacity: 0; transition: opacity 1.2s ease-in-out; }
        .carousel img.active { opacity: 1; }
        .overlay { position: absolute; top: 0; left: 0; width: 100%; height: 100%; background: linear-gradient(135deg, rgba(37, 117, 252, 0.45), rgba(106, 17, 203, 0.45)); z-index: 1; }
        .left-content { position: relative; z-index: 2; padding: 60px 40px; backdrop-filter: blur(2px); }
        .left-content h2 { font-size: 30px; margin: 15px 0 8px; font-weight: 600; }
        .left-content span { color: #ffeb3b; }
        .left-content p { font-size: 14px; margin-bottom: 25px; opacity: 0.9; line-height: 1.6; }
        .left-content a { color: #fff; text-decoration: none; border-bottom: 1px solid #fff; transition: 0.3s; }
        .left-content a:hover { opacity: 0.8; }
        .right-panel { flex: 1; padding: 60px 50px; display: flex; flex-direction: column; justify-content: center; animation: fadeInUp 0.7s ease; }
        @keyframes fadeInUp { 0% { opacity: 0; transform: translateY(15px); } 100% { opacity: 1; transform: translateY(0); } }
        .right-panel h3 { margin-bottom: 30px; font-size: 26px; color: #1d1d1d; text-align: center; letter-spacing: 0.3px; font-family: 'Poppins', sans-serif; font-weight: 800; }
        .input-group { margin-bottom: 22px; }
        .input-group label { display: block; font-size: 14px; color: #444; font-weight: 700; margin-bottom: 6px; }
        .input-group input { width: 100%; padding: 13px 14px; border-radius: 10px; border: 1px solid #ccc; outline: none; transition: all 0.3s; font-size: 15px; font-family: inherit; }
        .input-group input:focus { border-color: #2575fc; box-shadow: 0 0 6px rgba(37,117,252,0.35); }
        .login-btn { width: 100%; padding: 13px; border: none; border-radius: 10px; background: linear-gradient(135deg, #2575fc, #6a11cb); color: #fff; font-size: 16px; font-weight: 600; cursor: pointer; transition: all 0.3s ease; }
        .login-btn:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(37, 117, 252, 0.3); }
        .links { margin-top: 15px; text-align: right; }
        .links a { text-decoration: none; color: #2575fc; font-size: 14px; transition: 0.2s; }
        .links a:hover { text-decoration: underline; }
        .social-login { text-align: center; margin-top: 35px; }
        .social-login p { color: #777; margin-bottom: 15px; font-size: 14px; }
        .social-icons button { border: none; border-radius: 50%; margin: 0 6px; width: 40px; height: 40px; cursor: pointer; color: #fff; font-weight: bold; transition: transform 0.3s; font-size: 16px; }
        .google { background: #DB4437; } .facebook { background: #3B5998; } .twitter { background: #1DA1F2; }
        .social-icons button:hover { transform: scale(1.1); }
        @media (max-width: 768px) { .login-container { flex-direction: column; width: 95%; height: auto; } .left-panel, .right-panel { padding: 40px 30px; align-items: center; text-align: center; } .left-content h2 { font-size: 24px; } .right-panel { padding-top: 0; } }
        .alert { padding: 12px 15px; border-radius: 6px; margin-bottom: 20px; border: 1px solid #e74c3c; color: #e74c3c; background: rgba(231, 76, 60, 0.08); }
    </style>
</head>
<body>

<div class="login-container">
    <div class="left-panel">
        <div class="carousel">
            <img src="https://images.unsplash.com/photo-1598285385411-c5952310875e?auto=format&fit=crop&q=60&w=600" class="active" alt="Pharmacy 1">
            <img src="https://images.unsplash.com/photo-1583088580009-2d947c3e90a6?auto=format&fit=crop&q=80&w=689" alt="Pharmacy 3">
        </div>
        <div class="overlay"></div>

        <div class="left-content">
            <div class="logo" style="font-size: 40px;">💊</div>
            <h2>Welcome to <span>Pharmacy Portal</span></h2>
            <p>Sign in to securely manage prescriptions, stock, and orders.</p>
            <a href="#">www.hmsportal.com</a>
        </div>
    </div>

    <div class="right-panel">
        <h3 style="text-align:center; font-weight:800; color:#2575fc; margin-bottom:20px;">Pharmacist Login</h3>
        <form th:action="@{/pharmacist/login}" method="post">
            <div th:if="${error}" class="alert" th:text="${error}"></div>
            <div class="input-group">
                <label>Username</label>
                <input type="text" name="username" placeholder="Enter your username" required>
            </div>
            <div class="input-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="Enter your password" required>
            </div>

            <button type="submit" class="login-btn">Login</button>
            <div class="links">
                <a href="/pharmacist/register">Register Here..</a>
            </div>

            <div class="social-login">
                <p>Or continue with</p>
                <div class="social-icons">
                    <button type="button" class="google"><i class="fab fa-google"></i></button>
                    <button type="button" class="facebook"><i class="fab fa-facebook-f"></i></button>
                    <button type="button" class="twitter"><i class="fab fa-twitter"></i></button>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    const images = document.querySelectorAll('.carousel img');
    let current = 0;
    setInterval(() => { images[current].classList.remove('active'); current = (current + 1) % images.length; images[current].classList.add('active'); }, 4000);
    </script>
</body>
</html>
