import React from 'react';
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
  posts: ImmutablePropTypes.list,
};

export default PostList;
