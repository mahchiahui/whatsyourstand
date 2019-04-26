<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Create A Password</title>

  <!-- Custom fonts for this template-->
  <link href="html/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <!--<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
-->
  <!-- Custom styles for this template-->
  <link href="html/css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body class="bg-gradient-primary">
   <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-purple topbar mb-4 static-top shadow">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">

            <i class="fa fa-bars"></i>
          </button>
           <!-- Topbar Navbar -->
           
          
        
      
          <ul class="navbar-nav mx-auto">  <!-- top right bottom left-->

<li  style="margin-right: 30px;" > <a href="login-voter" class="btn btn-primary btn-icon-split btn-lg " style="background-color: #5f38a4; border-color: #5f38a4; opacity:.4;" >
                    <span class="icon text-white-50">
                      <i class="fas fa-flag"></i>
                    </span>
                    <span class="text"> Voter </span>
                  </a>
            </li>

            <li style="margin-right: 30px;"> <a href="login-candidate" class="btn btn-primary btn-icon-split btn-lg" style="background-color: #5f38a4; border-color: #5f38a4;">
                    <span class="icon text-white-50">
                      <i class="fas fa-flag"></i>
                    </span>
                    <span class="text"> Candidate </span>
                  </a>
            </li>

            <li style="margin-right: 30px;"> <a href="login-admin" class="btn btn-primary btn-icon-split btn-lg" style="background-color: #5f38a4; border-color: #5f38a4;">
                    <span class="icon text-white-50">
                      <i class="fas fa-flag"></i>
                    </span>
                    <span class="text"> Admin </span>
                  </a>
            </li>

          </ul>
        
          

        </nav>
        <!-- End of Topbar -->

  <div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
        <!--      <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>  -->
              <div class="col-lg-12">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">
                    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="home">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">What's Your Stand</sup></div>
                    </a></h1><h4>Create a password</h4>
                  </div>
                  <form class="user" method="post" action="TokenRegistration">
                    <div class="form-group" style="display: none">
                        <%String token = (String)request.getAttribute("token");%>
                      <input type="text" name="token" value="<%out.println(token);%>" style="display: none" placeholder="Enter Token">
                    </div>
                    <div class="form-group">
                      <input type="password" name="password" class="form-control form-control-user" id="exampleInputPassword" placeholder="Password">
                    </div>
                    <div class="form-group">
                      <input type="password" name="repPassword" class="form-control form-control-user" id="ReenterInputPassword" placeholder="Reenter Password">
                    </div>
                    
                    <input type="submit" class="btn btn-primary btn-user btn-block" value="Create A Password"/>
                  
                    
                  </form>
                  
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>

    </div>

  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="html/vendor/jquery/jquery.min.js"></script>
  <script src="html/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="html/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="html/js/sb-admin-2.min.js"></script>

</body>

</html>