import React, { PropTypes } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
import Post from './Post';

const PostList = ({ posts }) => (
  <div className="post-container">
    {posts.map(post =>
      <Post
        key={post.id}
        createdBy={post.createdBy}
        time={post.time}
        message={post.message}
      />
    )}
  </div>
);

PostList.propTypes = {
  posts: ImmutablePropTypes.listOf(
    ImmutablePropTypes.contains({
      id: PropTypes.string.isRequired,
      // groupId: PropTypes.string.isRequired,
      createdBy: PropTypes.string.isRequired,
      message: PropTypes.string.isRequired,
      time: PropTypes.oneOfType([
        PropTypes.string,
        PropTypes.instanceOf(Date), // TODO: Remove when not generating posts
      ]).isRequired,
    })
  ),
};

export default PostList;
