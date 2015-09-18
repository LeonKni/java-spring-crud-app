/**
 *  @author Leon K
 */

$(document).on('click', '.navbar-nav li', function () {
    $(".navbar-nav li").removeClass("active");
    $(this).addClass("active");
});