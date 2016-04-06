import React, { Component } from 'react';
import Post from './Post';

export default ({ posts }) => (
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
