import React, { Component } from 'react';
import Post from './Post';

export default ({ posts }) => (
  <div className="post-container">
    {posts.map((post, i) =>
      <Post
        key={i}
        createdBy={post.createdBy}
        time={post.time}
        message={post.message}
      />
    )}
  </div>
);
