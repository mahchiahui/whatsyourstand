<%@ page import="com.app.entity.Voter" %>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Help</title>

  <!-- Custom fonts for this template-->
  <link href="html/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <!--<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
-->
  <!-- Custom styles for this template-->
  <link href="html/css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="home">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">What's Your Stand</div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Privacy Policy -->
      <li class="nav-item active">
        <a class="nav-link" href="privacypolicy">
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span>Privacy Policy</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">
     

      <!-- Heading -->
      <div class="sidebar-heading">
        Sections
      </div>

      <!-- Nav Item - My Quetions Menu -->
      <li class="nav-item">
        <a class="nav-link" href="voter-myquestions" >
          <i class="fas fa-fw fa-folder"></i>
          <span>My Questions</span>
        </a>
      </li>

      <!-- Nav Item - Q & A  Menu -->
      <li class="nav-item">
        <a class="nav-link" href="voter">
          <i class="fas fa-fw fa-paw"></i>
          <span>Top Question & Answer</span>
        </a>
      </li>

      <!-- Divider -->
         <hr class="sidebar-divider my-0">

      <li class="nav-item active">
         
        <a class="nav-link" href="voter-help" style="opacity: .4">
          <i class="fas fa-fw fa-question-circle"></i>
          <span>Help</span></a>
      </li>

     

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>


          <!-- Topbar Navbar -->
          <ul class="navbar-nav ml-auto">

            <div class="topbar-divider d-none d-sm-block"></div>

            <!-- Nav Item - Rootuser Information -->
            <li class="nav-item dropdown no-arrow">
              <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <%Voter voter = (Voter)request.getAttribute("voter");%>
                <span class="mr-2 d-none d-lg-inline text-gray-600 small"><%out.println(voter.getUsername());%></span>
                <img class="img-profile rounded-circle" src="html/img/profile-pic.jpg">
              </a>
              <!-- Dropdown - Rootuser Information -->
              <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                  <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                  Logout
                </a>
              </div>
            </li>

          </ul>

        </nav>
        <!-- End of Topbar -->

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">Help</h1>
           </div>


          <!-- Content Row -->

           
           
           <div class="row">
              <div class="col-lg-6 d-none d-lg-block bg-password-image"></div>
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-2">Need Help?</h1>
                    <p class="mb-4">Please call us: 111-333-2222</p>
                  </div>
                    <p class="mb-4">We get it, stuff happens. Just enter your email address below and we'll contact you later!</p>
                  </div>
                  <form class="user">
                    <div class="form-group">
                      <p class="mb-4"><u>Please Note:</u> By inputting your email below, you have <b>agreed</b> that we can use your email address to contact you and answer your question(s).</p>
                      <label>Your Contact Email:</label>
                      <input type="email" class="form-control form-control-user" id="contactEmail" aria-describedby="emailHelp" placeholder="Enter Email Address...">
                      <label>confirm Your Email:</label>
                      <input type="email" class="form-control form-control-user" id="contactEmailAgain" aria-describedby="emailHelp" placeholder="Enter Email Address Again...">

                      <label>Questions:</label>
                      <input type="text" class="form-control form-control-user" id="question" placeholder="Please input your question here...">

                    </div>
                    <a href="#" class="btn btn-primary btn-user btn-block">
                      Submit Question
                    </a>
                  </form>
                </div>
              </div>
            </div>
 
            

          </div>
          <!-- end of firstrow -->

        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; What's your stand 2019</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true"><i class="fas fa-times"></i></span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="logout">Logout</a>
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

  <!-- Page level plugins -->
  <script src="html/vendor/chart.js/Chart.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="html/js/demo/chart-area-demo.js"></script>
  <script src="html/js/demo/chart-pie-demo.js"></script>

</body>

</html>
