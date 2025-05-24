import React, { useState } from 'react';
import { Input, Button, Form, message } from 'antd';
import axios from 'axios';

const NewGroupPage = () => {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [imageUrl, setImageUrl] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      await axios.post('/api/groups', {
        name,
        description,
        imageUrl,
      });

      message.success('그룹이 성공적으로 생성되었습니다!');
      // 입력창 초기화
      setName('');
      setDescription('');
      setImageUrl('');
    } catch (error) {
      console.error(error);
      message.error('그룹 생성 실패!');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 600, margin: '0 auto', padding: 20 }}>
      <h1>새 그룹 생성</h1>
      <Form layout="vertical" onSubmitCapture={handleSubmit}>
        <Form.Item label="그룹 이름">
          <Input
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="예: 스터디 그룹"
          />
        </Form.Item>

        <Form.Item label="그룹 설명">
          <Input.TextArea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="예: 함께 공부하는 스터디입니다."
            rows={4}
          />
        </Form.Item>

        <Form.Item label="이미지 URL">
          <Input
            value={imageUrl}
            onChange={(e) => setImageUrl(e.target.value)}
            placeholder="예: https://example.com/image.jpg"
          />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading}>
            그룹 생성
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default NewGroupPage;
