import React, { Component } from 'react';
import TimeAgo from 'react-timeago';
import PostsList from './PostsList';
import Topbar from './Topbar';

const names = ['John Doe', 'Patrick Von Lussenhoff'];
const message = `Lorem Ipsum dolor sit amet. `.repeat(20);

const generatePosts = (n, names, message) => (
  Array(n).fill({}).map((_, i) => ({
    username: names[Math.floor(Math.random() * names.length)],
    time: new Date(Date.now() - 8600000*i*100),
    message
  }))
);

export default class Dashboard extends Component {
  constructor(props) {
    super(props);

    this.state = {
      posts: generatePosts(10, names, message)
    };
  }

  render() {
    const { posts } = this.state;

    return (
      <div className="main">
        <Topbar title="Dashboard" />
        <PostsList posts={posts} />
      </div>
    );
  }
}
