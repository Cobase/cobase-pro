var React = require('react');
var TimeAgo = require('react-timeago');
var Linkify = require('react-linkify');
var $ = require('jquery');

var TweetList = React.createClass({
    render: function() {
        var tweetNodes = this.props.data.map(function(tweet, index) {
            return (
                <Tweet screenName={tweet.screenName} realName={tweet.realName} profileImageURL={tweet.profileImageURL} createdAgo={tweet.createdAgo} text={tweet.text} key={index}></Tweet>
            );
        });
        return (
            <div>
                {tweetNodes}
            </div>
        );
    }
});

var Tweet = React.createClass({
    render: function() {
        return (
            <li>
                <div className="timeline-item">
                    <h3 className="timeline-header">
                        <div className="row">
                            <div className="tweet-info">
                                <a href={'https://twitter.com/' + this.props.screenName}><img className="twitter-icon" src={this.props.profileImageURL} /></a>
                            </div>
                            <div className="tweet">
                                <div>{this.props.realName}</div>
                                <div><a href={'https://twitter.com/' + this.props.screenName}>{this.props.screenName}</a></div>
                            </div>
                        </div>
                    </h3>
                    <div className="timeline-body wordbreak">
                        <Linkify>{this.props.text}</Linkify>
                        <br/>
                        <span className="time ago"><i className="fa fa-clock-o"></i> <TimeAgo date={this.props.createdAgo}></TimeAgo></span>
                    </div>
                </div>
            </li>
        );
    }
});

export default React.createClass({
     loadTweetsFromServer: function() {
         $.ajax({
             url: this.props.url,
             dataType: 'json',
             cache: false,
             success: function(data) {
                 this.setState({data: data});
             }.bind(this),
             error: function(xhr, status, err) {
                 $('#twitter').text('Feed could not be loaded.');
             }.bind(this)
         });
     },
     getInitialState: function() {
         return {data: []};
     },
     componentDidMount: function() {
         this.loadTweetsFromServer();
     },
     render: function() {
         return (
             <ul className="twitter-timeline noline">
                 <TweetList data={this.state.data} />
             </ul>
         );
     }
 });