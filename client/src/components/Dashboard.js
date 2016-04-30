import React, { Component, PropTypes } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
import PostsList from './PostsList';
import Topbar from './Topbar';
import EmptyContent from './EmptyContent';

import { List } from 'immutable';
function generatePosts(n) {
  const names = ['John Doe', 'Patrick Von Lussenhoff'];

  return List(Array(n).fill({}).map((_, i) => ({
    createdBy: names[Math.floor(Math.random() * names.length)],
    time: new Date(Date.now() - 8600000 * i * 100),
    id: i,
    message: 'Lorem Ipsum dolor sit amet www.cobasepro.com '.repeat(20),
  })));
}

export default class Dashboard extends Component {
  constructor(props) {
    super(props);

    this.state = {
      posts: generatePosts(10),
    };
  }

  render() {
    const { posts } = this.state;

    return (
      <div className="main">
        <Topbar title="Dashboard" />
        {posts.size ?
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

Dashboard.propTypes = {
  posts: ImmutablePropTypes.listOf(
    ImmutablePropTypes.contains({
      createdBy: PropTypes.string.isRequired,
      // TODO: Enable when not using dummy data
      // groupId: PropTypes.string.isRequired,
      time: PropTypes.string.isRequired,
      message: PropTypes.string.isRequired,
    })
  ),
};
