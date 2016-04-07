import React, { Component } from 'react';
import PostsList from './PostsList';
import Topbar from './Topbar';
import EmptyContent from './EmptyContent';

const names = ['John Doe', 'Patrick Von Lussenhoff'];
const message = `Lorem Ipsum dolor sit amet www.cobasepro.com `.repeat(20);

const generatePosts = (n, names, message) => (
  Array(n).fill({}).map((_, i) => ({
    createdBy: names[Math.floor(Math.random() * names.length)],
    time: new Date(Date.now() - 8600000*i*100),
    id: i,
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
        {posts.length ?
          <PostsList posts={posts} />
          :
          <EmptyContent
            message="The Dashboard is empty! What now?"
          />
        }
      </div>
    );
  }
}
