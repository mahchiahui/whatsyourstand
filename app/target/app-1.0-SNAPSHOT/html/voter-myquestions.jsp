<%@ page import="java.util.List" %>
<%@ page import="com.app.entity.*" %>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>My Questions</title>

  <!-- ajax call for question -->
  <script type="text/javascript" language="javascript" src="html/js/ajax-question.js"></script>

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
        <a class="nav-link" href="voter-myquestions" style="opacity: .3;">
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
         
        <a class="nav-link" href="voter-help">
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
            <h1 class="h3 mb-0 text-gray-800">My Questions</h1>
           

    
          <a class="btn btn-primary dropdown-toggle" href="#" id="TelDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">A New Question 
            <i class="fas fa-plus"></i>
          </a>
           <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="TelDropdown">
                <h6 class="dropdown-header">
                 Create a new Question
                </h6>
            <a class="dropdown-item d-flex align-items-center" href="#">
                  <div class="mr-5">

                      <form class="question" style="text-align: right" method="post" action="question">
                          <div class="form-group">
                              <span style="float: left"><b>Title</b><br></span>
                              <input style="margin-bottom: 20px;width: 80%;display: inline-block;" type="text" class="form-control form-control-user" id="Title" placeholder="Enter Your Title Here" name="title">

                              <hr>
                              <br><span style="float: left"><b>Description</b></span>
                              <!-- ??? -->
                              <textarea rows="4" cols="50" name="description" placeholder="Please input your description Here."></textarea>
                              <br>
                              <input type="hidden" name="lasturl" value="voter-myquestions"/>

                              <input type="submit" class="btn btn-primary btn-user btn-block" value="submit question">
                          </div>
                      </form>

                  </div>
            </a>
          </div>
        
                </div>
            


          <!-- Content Row -->
 
            <div class="row">

            <!-- Earnings (Monthly) Card Example -->
            <div class="col-xl-10 col-md-10 mb-4">
              <!-- ******* PRIMARY Q&A CARD ******* -->
              <!-- jsp loaded Q&A CARD -->
              <%
                String userID = (String) request.getAttribute("userID");
                List<Question> questionList = (List<Question>) request.getAttribute("question_list");
                List<Status> statusList = (List<Status>) request.getAttribute("status_list");
                List<List<Answer>> answersList = (List<List<Answer>>) request.getAttribute("answer_list_of_list");
                List<List<Candidate>> candidateList = (List<List<Candidate>>) request.getAttribute("candidate_list_of_list");

                if (questionList != null && questionList.size() != 0) {
                  for (int i = 0; i < questionList.size(); i++) {
                    Question question = questionList.get(i);
                    Status status = statusList.get(i);

                    String statusUpvote = null;
                    String statusDownvote = null;
                    if (status.getStatusType() == 0) {
                      statusUpvote = "far fa-thumbs-up";
                      statusDownvote = "far fa-thumbs-down";
                    }
                    else if (status.getStatusType() == 1) {
                      statusUpvote = "fas fa-thumbs-up";
                      statusDownvote = "far fa-thumbs-down";
                    }
                    else {
                      statusUpvote = "far fa-thumbs-up";
                      statusDownvote = "fas fa-thumbs-down";
                    }


                    out.println("<div class=\"col-xl-10 col-md-10 mb-4\"><div class=\"card border-left-primary shadow h-100 py-2\">\n" +
                            "<div class=\"card-body\">\n" +
                            "<div class=\"row no-gutters align-items-center\">\n" +
                            "<div class=\"col mr-2\">\n" +

                    // Question
                            "<div class=\"text-xs font-weight-bold text-primary text-uppercase mb-1\" id=\"question-" +
                            question.getQuestionId() + "\"><span>" +
                            question.getLastModifiedTime() + "</span> \n" +
                            "<a  href=\"report?questionid=" +
                            question.getQuestionId() + "&lasturl=voter-myquestions" +
                            "\" class=\"report\"><span style=\"margin-right:1em;\"><i style=\"margin-right:1em;float: right\" class=\"fas fa-exclamation-circle\"></i>  </span></a>\n" +
                            "<a  href=\"#\" onclick=\"makeRequestQuestion(this)\" class=\"downvote\"> <span style=\"margin-right:1em;float: right\"><i style=\"float: right\" class=\"" +
                            statusDownvote + "\">" +
                            question.getDownvote() + "</i></span></a>\n" +
                            "<a  href=\"#\" onclick=\"makeRequestQuestion(this)\" class=\"upvote\"> <span style=\"margin-right:1em;float: right\"><i style=\"float: right\" class=\"" +
                            statusUpvote + "\">" +
                            question.getUpvote() + "</i></span></a>\n");
                    if (userID.equals(String.valueOf(question.getUserId()))) {
                        out.println("<a  href=\"question-update?questionid=" +
                            question.getQuestionId() + "&action=update&lasturl=voter-myquestions" +
                            "\" class=\"update\"> <span style=\"margin-right:1em;float: right\"><i class=\"far fa-edit\"></i></span></a>\n");
                        out.println("<a  href=\"question-update?questionid=" +
                            question.getQuestionId() + "&action=delete&lasturl=voter-myquestions" +
                            "\" class=\"delete\"> <span style=\"margin-right:1em;float: right\"><i class=\"fas fa-trash-alt\"></i></span></a>\n");
                    }

                    out.println("</div>\n" +
                            "<div class=\"h5 mb-0 font-weight-bold text-gray-800\">\n" +
                            question.getTitle() +
                            "<a class=\"nav-link collapsed\" href=\"#\" data-toggle=\"collapse\" data-target=\"#DesPages"+ i + "\" aria-expanded=\"true\" aria-controls=\"DesPages\" style=\"color:grey!important;float:right\">More</a>\n" +
                            "<div id=\"DesPages" + i +"\" class=\"collapse\" aria-labelledby=\"headingPages\" data-parent=\"#accordionSidebar\">\n" +
                            " <hr>\n" +
                            "<div class=\"bg-white py-2 collapse-inner rounded\" style=\"margin-bottom: 3px\">\n" +
                            "<h6 class=\"collapse-header\">  <span>Description</span> </h6>\n" +
                            "<div class=\"h5 mb-0 font-weight-bold text-gray-800\"><span class=\"mr-2 d-none d-lg-inline text-gray-600 small\">" +
                            question.getDescription() + " </span><br>\n" +
                            "</div>\n" +
                            "</div>\n" +
                            "</div>\n" +
                            "<hr> Candidate(s) Answer(s)\n" +
                            "<a class=\"nav-link collapsed\" href=\"#\" data-toggle=\"collapse\" data-target=\"#collapsePages" + i + "\" aria-expanded=\"true\" aria-controls=\"collapsePages\" style=\"float:right\">\n" +
                            "<i class=\"fas fa-ellipsis-h\"></i>" + "</a>\n" +
                            "<div id=\"collapsePages" + i + "\" class=\"collapse\" aria-labelledby=\"headingPages\" data-parent=\"#accordionSidebar\">\n" +
                            "<hr>\n");

                    // Answers
                    for (int j = 0; j < answersList.get(i).size(); j++) {
                      Answer answer = answersList.get(i).get(j);
                      Candidate candidate = candidateList.get(i).get(j);

                      out.println("<div class=\"bg-white py-2 collapse-inner rounded\" style=\"margin-bottom: 3px\">\n" +
                              "<h6 class=\"collapse-header\">\n" +
                              "<img class=\"img-profile rounded-circle\" src=\"" + candidate.getProfilePhotoPath() + "\">" +
                              candidate.getRealname() +
                              "</h6>\n" +
                              "<div class=\"h5 mb-0 font-weight-bold text-gray-800\"><span class=\"mr-2 d-none d-lg-inline text-gray-600 small\">" +
                              candidate.getLocation() + "</span><br>\n" +
                              "<span>\n" + answer.getContent() + "</span>\n" +
                              "</div>\n" +
                              "<br>\n" +
                              "<span>\n" +
                              "<a class=\"btn btn-primary btn-icon-split\" href=\"#\" style=\"margin-right: 2em;\"> <span class=\"icon text-white-50\" s><i class=\"far fa-thumbs-up\"></i></span>\n" +
                              "<span class=\"text\">" + answer.getUpvote() + "</span></a>\n" +
                              "<a class=\"btn btn-primary btn-icon-split\" href=\"#\" style=\"margin-right: 2em;\"> <span class=\"icon text-white-50\" s><i class=\"far fa-thumbs-down\"></i></span>\n" +
                              "<span class=\"text\">" + answer.getDownvote() + "</span></a>\n" +
                              "<span style=\"float: right\" class=\"mr-2 d-none d-lg-inline text-gray-600 small\">" +
                              answer.getLastModifiedTime() + "</span></span></div>\n");
                      if (j != answersList.get(i).size() - 1) {
                        out.println("<hr>\n");
                      }
                    }

                    out.println("</div></div></div></div></div></div></div>");

                  }
                }

              %>
                    
                  </div>
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
