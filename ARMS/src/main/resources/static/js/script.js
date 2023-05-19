window.addEventListener("load", function(){
    setTimeout(
        function open(event){
            document.querySelector(".popup").style.display = "block";
            var blur=document.getElementById('blur');
            blur.classList.toggle('active');
        },
        0
    )
  });
  
  document.querySelector("#close").addEventListener("click", function(){
    document.querySelector(".popup").style.display = "none";
    var blur=document.getElementById('blur');
            blur.classList.toggle('closed');
  });
  function toggleMenu() {
      var menuDropdown = document.getElementById("menu-dropdown");
      if (menuDropdown.style.display === "block") {
        menuDropdown.style.display = "none";
      } else {
        menuDropdown.style.display = "block";
      }
    }
  let slideIndex = 1;
    showSlides(slideIndex);
    
  function plusSlides(n) {
    showSlides(slideIndex += n);
    }
    
  function currentSlide(n) {
    showSlides(slideIndex = n);
    }
    
  function showSlides(n) {
    let i;
    let slides = document.getElementsByClassName("Slides");
    let dots = document.getElementsByClassName("dot");
    if (n > slides.length) {slideIndex = 1}    
    if (n < 1) {slideIndex = slides.length}
    for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";  
    }
    for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" active", "");
    }
    slides[slideIndex-1].style.display = "block";  
    dots[slideIndex-1].className += " active";
    }
    window.onscroll=function(){stickynav()};
    var navbar=document.getElementById("navbar");
    var sticky=navbar.offsetTop;
    function stickynav(){
        if(window.pageYOffset>=sticky){
            navbar.classList.add("sticky")
        }else{
            navbar.classList.remove("sticky");
        }
    }
  
  