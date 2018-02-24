// this file will be putting a flat clothlike set of verticies under the main content
// upon clicking a ring will grow from the click causing a ripple effect
var clickRing = function (clickX, clickY) {
    this.clickX = clickX;
    this.clickY = clickY;
}

$(document).ready(function () {
    $(window).click(function(e) {
        var relativeX = (e.pageX - $(e.target).offset().left),
            relativeY = (e.pageY - $(e.target).offset().top);
            
            console.log("X: " + relativeX + "  Y: " + relativeY);
    });
});