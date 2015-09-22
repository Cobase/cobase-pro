var React = require('react');
var $ = require('jquery');

var GroupPosts = require('./react/GroupPosts.js');
var TweetBox = require('./react/TweetBox.js');

var autosize = require('./autosize.min.js');

require('./../../../public/plugins/iCheck/icheck.js');
require('./../../../public/plugins/jQueryUI/jquery-ui-1.10.3.js');

require('./tag-it.js');
require('./bootstrap-tagsinput.js');

require('./../css/AdminLTE.css');
require('./../css/bootstrap-tagsinput.css');
require('./../css/cobase.css');
require('./../css/font-awesome.css');
require('./../css/skins/_all-skins.min.css');
require('./../jquery-ui/jquery-ui-flick.css');
require('./../css/jquery.tagit.css');
require('./../../../public/bootstrap/css/bootstrap.min.css');
require('./../../../public/plugins/iCheck/all.css');

/* Generate switchable tabs */
$(function() {
    $('ul.tabs').each(function(){
        var $active, $content, $links = $(this).find('a');

        $active = $($links.filter('[href="'+location.hash+'"]')[0] || $links[0]);
        $active.addClass('active');

        $content = $($active[0].hash);
        $links.not($active).each(function () {
          $(this.hash).hide();
        });

        $(this).on('click', 'a', function(e){
          $active.removeClass('active');
          $content.hide();

          $active = $(this);
          $content = $(this.hash);

          $active.addClass('active');
          $content.show();

          e.preventDefault();
        });
    });
});

$(function() {
    autosize($('.autosize'));

    //$(".post-content").linkify();

    var groupPostsContainer = document.getElementById('group-posts');

    if (groupPostsContainer) {
        React.render(
            <GroupPosts url={'/group/' + $('#group-posts').attr("data-group-id") + '/api/posts'}/>,
            groupPostsContainer
        );
    }

    var twitterContainer = document.getElementById('twitter');

    if (twitterContainer) {
        React.render(
            <TweetBox url={'/group/' + $('#twitter-feed').attr("data-group-id") + '/api/tweets'} />,
            twitterContainer
        );
    }
});

$(function () {
    $('input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
    });
});

$(document).ready(function() {
    $("#tags").tagit({
        preprocessTag: function(tag) {
            if (!tag) { return ''; }

            if (tag.substr(0, 1) == "#") {
                return tag;
            } else if (tag.substr(0, 1) == "@@") {
                return tag;
            } else {
                return "#" + tag;
            }

        }
    });
});